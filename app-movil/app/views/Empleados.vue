<template>
    <Page>
      <StackLayout>
        <Button text="Agregar Empleado" @tap="addEmpleado"></Button>
        <ListView for="empleado in empleados" v-if="empleados.length">
          <v-template>
            <Label :text="empleado.nombre" />
          </v-template>
        </ListView>
      </StackLayout>
    </Page>
  </template>
  
  <script>
  export default {
    data() {
      return {
        empleados: [],
      };
    },
    async created() {
      await this.fetchEmpleados();
    },
    methods: {
      async fetchEmpleados() {
        try {
          const response = await fetch('http://localhost:8081/empleados', {
            headers: { Authorization: `Bearer ${this.$store.state.token}` },
          });
          this.empleados = await response.json();
        } catch (error) {
          console.error(error);
        }
      },
      addEmpleado() {
        this.$navigateTo(AddEmpleado);
      },
    },
  };
  </script>
  