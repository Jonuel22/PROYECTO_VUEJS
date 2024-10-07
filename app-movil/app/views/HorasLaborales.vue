<!-- HorasLaborales.vue -->
<template>
    <Page>
      <ActionBar title="Gestión de Horas Laborales" />
      <StackLayout>
        <Button text="Cargar Horas Laborales y Empleados" @tap="loadData"></Button>
  
        <!-- Mostrar la lista de horas laborales -->
        <ListView v-if="horasLaborales.length" for="hora in horasLaborales">
          <v-template>
            <StackLayout>
              <Label :text="'Empleado: ' + hora.empleado.nombre" />
              <Label :text="'Fecha: ' + hora.fecha" />
              <Label :text="'Horas Trabajadas: ' + hora.horasTrabajadas" />
            </StackLayout>
          </v-template>
        </ListView>
        
        <Button text="Agregar Horas Laborales" @tap="addHorasLaborales"></Button>
      </StackLayout>
    </Page>
  </template>
  
  <script>
  export default {
    data() {
      return {
        horasLaborales: [],
        empleados: [],
      };
    },
    methods: {
      async loadData() {
        try {
          // Ejecutar en paralelo las peticiones a horas laborales y empleados
          const [horasResponse, empleadosResponse] = await Promise.all([
            fetch('http://10.0.2.2:8080/horas_laborales', {
              headers: { Authorization: `Bearer ${this.$store.state.token}` },
            }),
            fetch('http://10.0.2.2:8080/empleados', {
              headers: { Authorization: `Bearer ${this.$store.state.token}` },
            }),
          ]);
  
          // Si ambas respuestas son exitosas, las asignamos a los datos locales
          if (horasResponse.ok && empleadosResponse.ok) {
            this.horasLaborales = await horasResponse.json();
            this.empleados = await empleadosResponse.json();
          } else {
            alert('Error al cargar datos');
          }
        } catch (error) {
          console.error(error);
        }
      },
      addHorasLaborales() {
        // Lógica para agregar horas laborales
        this.$navigateTo(AddHorasLaborales);
      },
    },
  };
  </script>

