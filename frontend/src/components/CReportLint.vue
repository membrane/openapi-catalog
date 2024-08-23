<template>
  <div>
    <c-report-lint-summary :model="props.model" />
    <br />
    <q-card v-for="it of props.model" :key="it.id" bordered flat>
      <q-card-section>
        <div class="text-subtitle2">{{ it.rule?.name ?? 'Standardrule' }}</div>
      </q-card-section>
      <q-card-section>
        <q-markup-table bordered flat>
          <thead>
            <tr>
              <th scope="col">Code</th>
              <th scope="col">Severity</th>
              <th scope="col">Path</th>
              <th scope="col">Message</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="entry of it.content.sort((a, b) => a.severity - b.severity)" :key="entry.code">
              <td>{{ entry.code }}</td>
              <td :class="`text-${EErrorColor[entry.severity]}`">{{ EErrorLevel[entry.severity] }}</td>
              <td>{{ entry.path.join('/').replaceAll('//', '/') }}</td>
              <td>{{ entry.message }}</td>
            </tr>
          </tbody>
        </q-markup-table>
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup lang="ts">
import CReportLintSummary from 'components/CReportLintSummary.vue'
import type LintReport from 'src/model/document/LintReport'
import EErrorColor from 'src/model/enums/EErrorColor'
import EErrorLevel from 'src/model/enums/EErrorLevel'

const props = defineProps<{ model: LintReport[] }>()
</script>
