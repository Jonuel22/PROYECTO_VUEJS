<template>
    <Page>
      <ActionBar title="Agregar Asistencia" />
      <StackLayout>
        <ListPicker v-model="empleadoSeleccionado" :items="empleados" displayMember="nombre" />
        <DatePicker v-model="fecha" />
        <TimePicker v-model="horaEntrada" />
        <TimePicker v-model="horaSalida" />
        
        <Button text="Confirmar" @tap="guardarAsistencia" />
        <Button text="Cancelar" @tap="cerrar" />
      </StackLayout>
    </Page>
  </template>
  
  <script>
  export default {
    props: ['empleados'],
    data() {
      return {
        empleadoSeleccionado: this.empleados.length ? this.empleados[0] : null, // Selecciona el primer empleado por defecto
        fecha: new Date(),
        horaEntrada: new Date(),
        horaSalida: new Date(),
      };
    },
    methods: {
      guardarAsistencia() {
        if (!this.empleadoSeleccionado) {
          alert("Por favor, selecciona un empleado.");
          return;
        }
        
        this.$modal.close({
          empleado: this.empleadoSeleccionado,
          fecha: this.fecha,
          horaEntrada: this.horaEntrada,
          horaSalida: this.horaSalida,
        });
      },
      cerrar() {
        this.$modal.close(null);
      }
    }
  };
  </script>
  