<template>
  <Page>
    <ActionBar title="Gestión de Asistencias" />
    
    <FlexboxLayout flexDirection="column" height="100%">
      <!-- Mostrar la lista de asistencias -->
      <ListView v-if="asistencias.length" :items="asistencias" class="list-container" flexGrow="1">
        <v-template v-for="asistencia in asistencias" :key="asistencia.id">
          <StackLayout class="list-item">
            <Label :text="'Empleado: ' + asistencia.empleado.nombre" class="list-item-text" />
            <Label :text="'Fecha: ' + asistencia.fecha" class="list-item-text" />
            <Label :text="'Hora de Entrada: ' + asistencia.horaEntrada" class="list-item-text" />
            <Label :text="'Hora de Salida: ' + asistencia.horaSalida" class="list-item-text" />
          </StackLayout>
        </v-template>
      </ListView>
      
      <!-- Botones para agregar, editar y eliminar asistencia -->
      <StackLayout class="button-group" flexShrink="0">
        <Button text="Agregar Asistencia" class="primary-button" @tap="abrirModalAgregar" />
        <Button text="Editar Asistencia" class="secondary-button" @tap="abrirModalEditarSeleccionada" />
        <Button text="Eliminar Asistencia" class="delete-button" @tap="eliminarAsistenciaSeleccionada" />
      </StackLayout>
    </FlexboxLayout>
  </Page>
</template>

<script>
import AgregarAsistenciaModal from './AgregarAsistenciaModal.vue';
import EditarAsistenciaModal from './EditarAsistenciaModal.vue';

export default {
  components: {
    AgregarAsistenciaModal,
    EditarAsistenciaModal
  },
  data() {
    return {
      asistencias: [], // Lista de asistencias
      empleados: [], // Lista de empleados cargada desde el servidor
      asistenciaSeleccionada: null, // Para manejar la asistencia seleccionada
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

    // Abrir modal para agregar una asistencia
    async abrirModalAgregar() {
      const result = await this.$showModal(AgregarAsistenciaModal, {
        props: { empleados: this.empleados },
        fullscreen: true
      });

      if (result && result.empleado && result.empleado.id) {
        // Aquí puedes hacer la lógica para agregar la asistencia
        const response = await fetch('http://10.0.2.2:8080/asistencias', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            empleado: { id: result.empleado.id },
            fecha: result.fecha.toISOString().split('T')[0],
            horaEntrada: result.horaEntrada.toTimeString().split(' ')[0],
            horaSalida: result.horaSalida.toTimeString().split(' ')[0],
          })
        });
        if (response.ok) this.loadData();
      } else {
        alert('Error: El empleado no es válido.');
      }
    },

    // Abrir modal para editar la asistencia seleccionada
    async abrirModalEditarSeleccionada() {
      if (!this.asistenciaSeleccionada) {
        alert('Por favor selecciona una asistencia');
        return;
      }
      
      const result = await this.$showModal(EditarAsistenciaModal, {
        props: { asistencia: this.asistenciaSeleccionada, empleados: this.empleados },
        fullscreen: true
      });

      if (result && result.empleado && result.empleado.id) {
        const url = `http://10.0.2.2:8080/asistencias/${this.asistenciaSeleccionada.id}`;
        const response = await fetch(url, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            empleado: { id: result.empleado.id },
            fecha: result.fecha.toISOString().split('T')[0],
            horaEntrada: result.horaEntrada.toTimeString().split(' ')[0],
            horaSalida: result.horaSalida.toTimeString().split(' ')[0],
          })
        });
        if (response.ok) this.loadData();
      } else {
        alert('Error: El empleado no es válido.');
      }
    },

    // Eliminar la asistencia seleccionada
    async eliminarAsistenciaSeleccionada() {
      if (!this.asistenciaSeleccionada) {
        alert('Por favor selecciona una asistencia');
        return;
      }

      const confirmDelete = confirm('¿Estás seguro de eliminar esta asistencia?');
      if (!confirmDelete) return;

      try {
        const response = await fetch(`http://10.0.2.2:8080/asistencias/${this.asistenciaSeleccionada.id}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          alert('Asistencia eliminada');
          this.loadData();
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
/* Contenedor principal */
.list-container {
  margin: 16px 0;
  height: 400px;
}

.list-item {
  padding: 16px;
  background-color: white;
  margin-bottom: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.list-item-text {
  font-size: 16px;
  margin-bottom: 8px;
}

/* Grupo de botones en la parte inferior */
.button-group {
  padding: 16px;
  background-color: #f5f5f5;
  justify-content: space-between;
}

.primary-button {
  background-color: #1976d2;
  color: white;
}

.secondary-button {
  background-color: #ff9800;
  color: white;
}

.delete-button {
  background-color: #f44336;
  color: white;
}
</style>
