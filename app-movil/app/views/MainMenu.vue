<template>
  <Page class="page">
    <ActionBar title="Gestión de Asistencias" class="action-bar" />
    <StackLayout class="main-layout">
      <Button text="Usuarios" @tap="goToUsuarios" class="menu-button"></Button>
      <Button text="Empleados" @tap="goToEmpleados" class="menu-button"></Button>
      <Button text="Asistencias" @tap="goToAsistencias" class="menu-button"></Button>
      <Button text="Horas Laborales" @tap="goToHorasLaborales" class="menu-button"></Button>
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

<style scoped>
.page {
  background-color: #f8f8f8;
}

.action-bar {
  background-color: #6200ee;
  color: white;
}

.main-layout {
  margin: 20;
  padding: 20;
  align-items: center;
}

.menu-button {
  width: 80%;
  height: 50;
  margin: 10 0;
  background-color: #6200ee;
  color: white;
  font-weight: bold;
  border-radius: 10;
  text-align: center;
}

.menu-button:active {
  background-color: #3700b3;
}
</style>
