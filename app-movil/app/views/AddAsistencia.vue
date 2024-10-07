<template>
    <Page>
      <ActionBar title="Agregar Asistencia" />
      <StackLayout>
        <!-- Picker para seleccionar el empleado -->
        <Picker v-model="empleadoSeleccionado" :items="empleados" itemDisplay="nombre" hint="Seleccionar Empleado" />
  
        <!-- Selección de fecha y hora -->
        <DatePicker v-model="fecha" />
        <TimePicker v-model="horaEntrada" />
        <TimePicker v-model="horaSalida" />
  
        <!-- Botón para guardar la asistencia -->
        <Button text="Guardar Asistencia" @tap="guardarAsistencia" />
      </StackLayout>
    </Page>
  </template>
  
  <script>
  export default {
    data() {
      return {
        empleados: [], // Lista de empleados cargada desde el servidor
        empleadoSeleccionado: null, // ID del empleado seleccionado
        fecha: new Date(),
        horaEntrada: new Date(),
        horaSalida: new Date(),
      };
    },
    methods: {
      async cargarEmpleados() {
        try {
          const response = await fetch('http://10.0.2.2:8080/empleados');
          if (response.ok) {
            this.empleados = await response.json();
          } else {
            alert('Error al cargar empleados');
          }
        } catch (error) {
          console.error('Error al cargar empleados:', error);
          alert('Hubo un problema al cargar los empleados.');
        }
      },
      async guardarAsistencia() {
        try {
          const response = await fetch('http://10.0.2.2:8080/asistencias', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              empleadoId: this.empleadoSeleccionado, // Solo el ID del empleado seleccionado
              fecha: this.fecha.toISOString().split('T')[0], // Formato de fecha YYYY-MM-DD
              horaEntrada: this.horaEntrada.toTimeString().split(' ')[0], // Formato de hora HH:MM:SS
              horaSalida: this.horaSalida.toTimeString().split(' ')[0], // Formato de hora HH:MM:SS
            }),
          });
  
          if (response.ok) {
            alert('Asistencia guardada exitosamente');
            this.$navigateBack();
          } else {
            alert('Error al guardar la asistencia');
          }
        } catch (error) {
          console.error('Error al guardar la asistencia:', error);
          alert('Hubo un problema al guardar la asistencia.');
        }
      },
    },
    created() {
      this.cargarEmpleados(); // Cargar la lista de empleados cuando el componente se crea
    },
  };
  </script>
  