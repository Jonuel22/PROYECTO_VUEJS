<template>
    <Page>
      <StackLayout>
        <Button text="Usuarios" @tap="goToUsuarios"></Button>
        <Button text="Empleados" @tap="goToEmpleados"></Button>
        <Button text="Asistencias" @tap="goToAsistencias"></Button>
        <Button text="Horas Laborales" @tap="goToHorasLaborales"></Button>
      </StackLayout>
    </Page>
  </template>
  
  <script>
  import Usuarios from './Usuarios.vue';
  import Empleados from './Empleados.vue';
  import Asistencias from './Asistencias.vue';
  import HorasLaborales from './HorasLaborales.vue';
  
  export default {
    methods: {
      goToUsuarios() {
        this.$navigateTo(Usuarios);
      },
      goToEmpleados() {
        this.$navigateTo(Empleados);
      },
      goToAsistencias() {
        this.$navigateTo(Asistencias);
      },
      goToHorasLaborales() {
        this.$navigateTo(HorasLaborales);
      },
    },
  };
  </script>
  