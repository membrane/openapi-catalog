<template>
  <q-timeline color="secondary">
    <q-timeline-entry v-for="it of majorVersion?.documents" :key="it.id" :subtitle="date.formatDate(it.timestamp, 'YYYY-MM-DD HH:mm:ss')">
      <template #title>Version {{ it.version }}</template>
      <c-report-diff-summary :model="it.report.diffReport" />
      <q-expansion-item v-if="it.report.diffReport?.report?.changes" expand-separator header-class="text-subtitle1" label="Diffreport">
        <q-card>
          <q-card-section>
            <c-report-diff :model="it.report.diffReport" />
          </q-card-section>
        </q-card>
      </q-expansion-item>
      <q-expansion-item expand-separator header-class="text-subtitle1" label="Lintingreport">
        <q-card>
          <q-card-section>
            <c-report-lint :model="it.report.lintReports" />
          </q-card-section>
        </q-card>
      </q-expansion-item>
    </q-timeline-entry>
  </q-timeline>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { date } from 'quasar'
import CReportDiff from 'components/CReportDiff.vue'
import CReportDiffSummary from 'components/CReportDiffSummary.vue'
import CReportLint from 'components/CReportLint.vue'
import type MajorVersion from 'src/model/MajorVersion'
import { useFetch } from 'src/utils/Fetch'

const props = defineProps<{ id: string; version: string }>()

const majorVersion = ref<MajorVersion>()

onMounted(() =>
  useFetch()
    .get(`specifications/${props.id}/majorversions/${props.version}`)
    .then((response) => response.json())
    .then((json: MajorVersion) => (majorVersion.value = json)),
)
</script>
