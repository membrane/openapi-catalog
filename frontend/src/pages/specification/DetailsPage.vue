<template>
  <div class="q-pa-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">Documents</div>
      </q-card-section>
      <q-card-section v-if="specification?.update">
        Automatic update via
        <br />
        <a :href="specification.update.url.url" target="_blank">{{ specification.update.url.url }}</a>
        <br />
        every {{ specification.update.interval }} Seconds
      </q-card-section>
      <q-card-section v-else>
        <q-uploader accept=".yaml" field-name="file" label="Upload new version" :url="`${fetch.baseUrl}documents?specificationId=${props.id}`" @uploaded="reload()" />
      </q-card-section>
      <q-card-section v-for="it of specification.majorVersions" :key="it.id">
        <q-table :columns="columns" :filter="filter" :rows="it.documents" :title="`Version ${it.version}`">
          <template #top-right>
            <q-input v-model="filter" debounce="300" dense placeholder="Search">
              <template #append>
                <q-icon :name="mdiMagnify" />
              </template>
            </q-input>
            <d-upload-linting-rule :id="it.id" class="q-ml-lg" @save="reload()" />
            <q-btn class="q-ml-lg" :icon="mdiHistory" label="Changelog" outline :to="{ name: 'specification-changelog', params: { id: specification.id, version: it.version } }" />
          </template>
          <template #body-cell-diff="cell">
            <q-td class="cursor-pointer" @click="router.push({ name: 'specification-changelog', params: { id: specification.id, version: it.version } })">
              <c-report-diff-summary v-if="cell.value" class="cursor-pointer" :model="cell.value" />
            </q-td>
          </template>
          <template #body-cell-lint="cell">
            <q-td class="cursor-pointer" @click="router.push({ name: 'document-details', params: { id: cell.value.id }, query: { tab: 'lint' } })">
              <c-report-lint-summary v-if="cell.value.report.lintReports" :model="cell.value.report.lintReports" />
            </q-td>
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
          <template #body-cell-actions="cell">
            <q-td>
              <q-btn color="light-green-14" label="{...}" round text-color="black" @click="router.push({ name: 'document-details', params: { id: cell.value } })">
                <q-tooltip>SwaggerUI</q-tooltip>
              </q-btn>
            </q-td>
          </template>
        </q-table>
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { date, type QTableProps } from 'quasar'
import { mdiHistory, mdiMagnify } from '@quasar/extras/mdi-v7'
import CReportDiffSummary from 'components/CReportDiffSummary.vue'
import CReportLintSummary from 'components/CReportLintSummary.vue'
import DUploadLintingRule from 'components/specification/DUploadLintingRule.vue'
import Document from 'src/model/Document'
import Specification from 'src/model/Specification'
import { useFetch } from 'src/utils/Fetch'

const props = defineProps<{ id: string }>()

const router = useRouter()

const fetch = useFetch()

const columns: QTableProps['columns'] = [
  { align: 'left', field: (row: Document) => row.title, label: 'Title', name: 'title' },
  { align: 'left', field: (row: Document) => row.servers, label: 'Servers', name: 'servers' },
  { align: 'left', field: (row: Document) => row.version, label: 'Version', name: 'version' },
  { align: 'left', field: (row: Document) => row.openapiVersion, label: 'OpenAPI', name: 'openapi' },
  { align: 'left', field: (row: Document) => row.report.diffReport, label: 'Versioning', name: 'diff' },
  { align: 'left', field: (row: Document) => row, label: 'Linting', name: 'lint' },
  { align: 'left', field: (row: Document) => row.timestamp, format: (val: Date) => date.formatDate(val, 'YYYY-MM-DD HH:mm:ss'), label: 'Uploaded', name: 'date' },
  { align: 'left', field: (row: Document) => row.id, label: 'Actions', name: 'actions' },
]
const filter = ref()
const specification = ref(new Specification())

const reload = async () =>
  await fetch
    .get(`specifications/${props.id}`)
    .then((response) => response.json())
    .then((json: Specification) => (specification.value = json))

onMounted(() => reload())
</script>
