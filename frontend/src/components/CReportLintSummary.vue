<template>
  <q-markup-table dense flat separator="vertical">
    <tbody>
      <tr>
        <td v-for="it of sortedReports" :key="it.id">
          <div class="row">
            <div class="col-auto">
              <p>{{ it?.rule?.name ?? 'Standardrule' }}</p>
              <div v-for="(v, k) in countChanges(it)" :key="k" :class="`row text-${EErrorColor[k]}`">
                <div class="col">{{ v }}</div>
                <div class="col">{{ EErrorLevel[k] }}</div>
              </div>
            </div>
          </div>
        </td>
      </tr>
    </tbody>
  </q-markup-table>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import type LintReport from 'src/model/document/LintReport'
import type IReportLintEntry from 'src/model/document/IReportLintEntry'
import EErrorColor from 'src/model/enums/EErrorColor'
import EErrorLevel from 'src/model/enums/EErrorLevel'

const props = defineProps<{ model: LintReport[] }>()

const countChanges = (lr: LintReport) =>
  lr.content.reduce((acc: Record<number, number>, current: IReportLintEntry) => {
    acc[current.severity] = (acc[current.severity] ?? 0) + 1
    return acc
  }, {})

const localCompare = (a: string, b: string | undefined) => (b ? a.localeCompare(b) : 1)

const sortedReports = computed(() => props.model.toSorted((a, b) => (a.rule?.name ? localCompare(a.rule.name, b.rule?.name) : -1)))
</script>
