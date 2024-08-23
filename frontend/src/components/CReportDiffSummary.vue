<template>
  <q-markup-table dense flat>
    <tbody>
      <tr v-if="countChanges.breakingChanges" class="text-negative">
        <td class="q-table--col-auto-width text-right">{{ countChanges.breakingChanges }}</td>
        <td>Breaking changes</td>
      </tr>
      <tr v-if="countChanges.totalChanges">
        <td class="q-table--col-auto-width text-right">{{ countChanges.totalChanges }}</td>
        <td>Total changes</td>
      </tr>
      <tr v-if="!countChanges.totalChanges">
        <td :class="`q-table--col-auto-width text-bold text-${EErrorColor[EErrorLevel.Info]}`" colspan="2">No change detected!</td>
      </tr>
      <tr v-if="!countChanges.breakingChanges && countChanges.totalChanges && !hasVersionChange">
        <td :class="`q-table--col-auto-width text-bold text-${EErrorColor[EErrorLevel.Warnings]}`" colspan="2">Version number was not changed!</td>
      </tr>
      <tr v-if="countChanges.breakingChanges">
        <td :class="`q-table--col-auto-width text-bold text-${EErrorColor[EErrorLevel.Errors]}`" colspan="2">Breaking change without majorversion bump!</td>
      </tr>
    </tbody>
  </q-markup-table>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import type IReportDiff from 'src/model/document/IReportDiff'
import type IReportDiffSummary from 'src/model/document/IReportDiffSummary'
import EErrorColor from 'src/model/enums/EErrorColor'
import EErrorLevel from 'src/model/enums/EErrorLevel'

const props = defineProps<{ model: IReportDiff | undefined }>()

const countChanges = computed(() =>
  Object.values(props.model?.report.reportSummary ?? {}).reduce(
    (acc: IReportDiffSummary, cur: IReportDiffSummary) => {
      acc.breakingChanges += cur.breakingChanges
      acc.totalChanges += cur.totalChanges

      return acc
    },
    { breakingChanges: 0, totalChanges: 0 },
  ),
)

const hasVersionChange = computed(() => props.model?.report.changes.some((it: Record<string, string>) => it['property'] === 'version') ?? false)
</script>
