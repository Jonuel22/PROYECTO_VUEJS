<template>
    <Page>
      <StackLayout>
        <Button text="Cargar Usuarios y Empleados" @tap="loadData"></Button>
        <ListView for="user in users" v-if="users.length">
          <v-template>
            <Label :text="user.nombre" />
          </v-template>
        </ListView>
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
        users: [],
        empleados: [],
      };
    },
    methods: {
      async loadData() {
        try {
          // Ejecutar en paralelo
          const [usersResponse, empleadosResponse] = await Promise.all([
            fetch('http://10.0.2.2:8080/usuarios', {
              headers: { Authorization: `Bearer ${this.$store.state.token}` },
            }),
            fetch('http://10.0.2.2:8080/empleados', {
              headers: { Authorization: `Bearer ${this.$store.state.token}` },
            }),
          ]);
  
          if (usersResponse.ok && empleadosResponse.ok) {
            this.users = await usersResponse.json();
            this.empleados = await empleadosResponse.json();
          } else {
            alert('Error al cargar datos');
          }
        } catch (error) {
          console.error(error);
        }
      },
    },
  };
  </script>
  