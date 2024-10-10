<template>
    <Page>
      <ActionBar title="Editar Asistencia" />
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
    props: ['asistencia', 'empleados'],
    data() {
      return {
        empleadoSeleccionado: this.asistencia && this.asistencia.empleado ? this.asistencia.empleado : this.empleados[0], // Selecciona el empleado de la asistencia o el primer empleado
        fecha: new Date(this.asistencia.fecha),
        horaEntrada: new Date(this.asistencia.horaEntrada),
        horaSalida: new Date(this.asistencia.horaSalida),
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
  