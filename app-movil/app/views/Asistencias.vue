<template>
  <Page>
    <ActionBar title="Gestión de Asistencias" />
    <StackLayout>
      <!-- Botón para cargar asistencias y empleados -->
      <Button text="Cargar Asistencias y Empleados" @tap="loadData" />

      <!-- Mostrar la lista de asistencias -->
      <ListView v-if="asistencias.length" :items="asistencias">
        <v-template v-for="asistencia in asistencias" :key="asistencia.id">
          <StackLayout>
            <Label :text="'Empleado: ' + asistencia.empleado.nombre" />
            <Label :text="'Fecha: ' + asistencia.fecha" />
            <Label :text="'Hora de Entrada: ' + asistencia.horaEntrada" />
            <Label :text="'Hora de Salida: ' + asistencia.horaSalida" />
            
            <!-- Botones para editar y eliminar asistencia -->
            <Button text="Editar" @tap="editarAsistencia(asistencia)" />
            <Button text="Eliminar" @tap="eliminarAsistencia(asistencia.id)" />
          </StackLayout>
        </v-template>
      </ListView>

      <!-- Formulario para agregar o editar asistencia -->
      <StackLayout v-if="isEditing || isAdding">
        <!-- Usamos el componente Picker correctamente -->
        <Picker v-model="empleadoSeleccionado" :items="empleados" itemDisplay="nombre" />
        <DatePicker v-model="fecha" />
        <TimePicker v-model="horaEntrada" />
        <TimePicker v-model="horaSalida" />
        <Button text="Guardar Asistencia" @tap="guardarAsistencia" />
      </StackLayout>

      <!-- Botón para mostrar el formulario de agregar asistencia -->
      <Button text="Agregar Asistencia" v-if="!isEditing && !isAdding" @tap="agregarNuevaAsistencia" />
    </StackLayout>
  </Page>
</template>

<script>
export default {
  data() {
    return {
      asistencias: [], // Lista de asistencias
      empleados: [], // Lista de empleados cargada desde el servidor
      empleadoSeleccionado: null, // Empleado seleccionado para el formulario
      fecha: new Date(), // Fecha de asistencia
      horaEntrada: new Date(), // Hora de entrada
      horaSalida: new Date(), // Hora de salida
      isEditing: false, // Bandera para saber si estamos editando
      isAdding: false, // Bandera para saber si estamos agregando
      asistenciaEditada: null, // Asistencia que se está editando
    };
  },
  methods: {
    // Cargar asistencias y empleados
    async loadData() {
      try {
        const [asistenciasResponse, empleadosResponse] = await Promise.all([
          fetch('http://10.0.2.2:8080/asistencias'),
          fetch('http://10.0.2.2:8080/empleados'),
        ]);

        if (asistenciasResponse.ok && empleadosResponse.ok) {
          this.asistencias = await asistenciasResponse.json();
          this.empleados = await empleadosResponse.json();
        } else {
          alert('Error al cargar los datos');
        }
      } catch (error) {
        console.error('Error al cargar datos:', error);
        alert('Hubo un problema al cargar los datos.');
      }
    },

    // Agregar nueva asistencia
    agregarNuevaAsistencia() {
      this.isAdding = true;
      this.isEditing = false;
      this.empleadoSeleccionado = null;
      this.fecha = new Date();
      this.horaEntrada = new Date();
      this.horaSalida = new Date();
    },

    // Editar una asistencia existente
    editarAsistencia(asistencia) {
      this.isEditing = true;
      this.isAdding = false;
      this.asistenciaEditada = asistencia;
      this.empleadoSeleccionado = asistencia.empleado;
      this.fecha = new Date(asistencia.fecha);
      this.horaEntrada = new Date(asistencia.horaEntrada);
      this.horaSalida = new Date(asistencia.horaSalida);
    },

    // Guardar asistencia (nuevo o editado)
    async guardarAsistencia() {
      const url = this.isEditing
        ? `http://10.0.2.2:8080/asistencias/${this.asistenciaEditada.id}`
        : 'http://10.0.2.2:8080/asistencias';
      const method = this.isEditing ? 'PUT' : 'POST';

      try {
        const response = await fetch(url, {
          method,
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            empleado: this.empleadoSeleccionado,
            fecha: this.fecha.toISOString().split('T')[0], // Formato de fecha YYYY-MM-DD
            horaEntrada: this.horaEntrada.toTimeString().split(' ')[0], // Formato de hora HH:MM:SS
            horaSalida: this.horaSalida.toTimeString().split(' ')[0], // Formato de hora HH:MM:SS
          }),
        });

        if (response.ok) {
          alert(this.isEditing ? 'Asistencia actualizada' : 'Asistencia agregada');
          this.isEditing = false;
          this.isAdding = false;
          this.loadData(); // Recargar la lista de asistencias
        } else {
          alert('Error al guardar la asistencia');
        }
      } catch (error) {
        console.error('Error al guardar la asistencia:', error);
        alert('Hubo un problema al guardar la asistencia.');
      }
    },

    // Eliminar una asistencia
    async eliminarAsistencia(id) {
      const confirmDelete = confirm('¿Estás seguro de eliminar esta asistencia?');
      if (!confirmDelete) return;

      try {
        const response = await fetch(`http://10.0.2.2:8080/asistencias/${id}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          alert('Asistencia eliminada');
          this.loadData(); // Recargar la lista de asistencias
        } else {
          alert('Error al eliminar la asistencia');
        }
      } catch (error) {
        console.error('Error al eliminar la asistencia:', error);
        alert('Hubo un problema al eliminar la asistencia.');
      }
    },
  },
  created() {
    this.loadData(); // Cargar asistencias y empleados cuando se crea el componente
  },
};
</script>

<style scoped>
  StackLayout {
    padding: 16px;
  }

  Button {
    margin-top: 20px;
  }

  ListView {
    height: auto;
  }
</style>
