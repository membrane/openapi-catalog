<template>
  <div class="q-pa-md">
    <q-table :columns="columns" :filter="filter" :rows="rows" title="Specifications" @row-click="(evt: Event, row: Specification, index: number) => router.push({ name: 'specification-details', params: { id: row.id } })">
      <template #top-left>
        <d-upload-specification @save="reload()" />
      </template>
      <template #top-right>
        <q-input v-model="filter" borderless dense debounce="300" placeholder="Search">
          <template #append>
            <q-icon :name="mdiMagnify" />
          </template>
        </q-input>
      </template>
      <template #body-cell-servers="cell">
        <q-td>
          <ul style="margin: 0; padding: 0">
            <li v-for="(value, key) in cell.value" :key="key">
              {{ key }}
              <br />
              {{ value }}
            </li>
          </ul>
        </q-td>
      </template>
      <template #body-cell-versions="cell">
        <q-td>
          <q-btn v-for="value of cell.value" :key="value.id" class="row" color="primary" dense flat :label="value.version" :to="{ name: 'document-details', params: { id: value.id } }" />
        </q-td>
      </template>
    </q-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { date, type QTableProps } from 'quasar'
import { mdiMagnify } from '@quasar/extras/mdi-v7'
import DUploadSpecification from 'components/specification/DUploadSpecification.vue'
import type Specification from 'src/model/Specification'
import { useFetch } from 'src/utils/Fetch'

const router = useRouter()

const fetch = useFetch()

const columns: QTableProps['columns'] = [
  {
    align: 'left',
    field: (row: Specification) => row.majorVersions.at(0)?.documents.at(0)?.title,
    label: 'Title',
    name: 'title',
  },
  { align: 'left', field: (row: Specification) => row.majorVersions.at(0)?.documents.at(0)?.servers, label: 'Servers', name: 'servers' },
  { align: 'left', field: (row: Specification) => row.majorVersions.at(0)?.documents, label: 'Versions', name: 'versions' },
  { align: 'left', field: (row: Specification) => row.majorVersions.at(0)?.documents.at(0)?.timestamp, format: (val: Date) => date.formatDate(val, 'YYYY-MM-DD HH:mm:ss'), label: 'Last update', name: 'timestamp' },
]
const filter = ref()
const rows = ref<Specification[]>([])

const reload = async () =>
  await fetch
    .get('specifications')
    .then((response) => response.json())
    .then((json: Specification[]) => (rows.value = json))

onMounted(() => reload())
</script>
