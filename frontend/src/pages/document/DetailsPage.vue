<template>
  <div v-if="document">
    <q-tabs v-model="tab" active-color="secondary" align="justify" indicator-color="primary">
      <q-tab label="SwaggerUI" name="swaggerui" />
      <q-tab label="Lint Report" name="lint" />
    </q-tabs>
    <q-separator />
    <q-tab-panels v-model="tab">
      <q-tab-panel name="swaggerui">
        <c-swagger-u-i :model="document.content" />
      </q-tab-panel>
      <q-tab-panel name="lint">
        <c-report-lint :model="document.report.lintReports" />
      </q-tab-panel>
    </q-tab-panels>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import CReportLint from 'components/CReportLint.vue'
import CSwaggerUI from 'components/document/CSwaggerUI.vue'
import Document from 'src/model/Document'
import { useFetch } from 'src/utils/Fetch'

const props = defineProps<{ id: string }>()

const route = useRoute()

const fetch = useFetch()

const document = ref<Document>()
const tab = ref(route.query['tab']?.toString() ?? 'swaggerui')

onMounted(() => {
  return fetch
    .get(`documents/${props.id}`)
    .then((response) => response.json())
    .then((json: Document) => (document.value = json))
})
</script>
