<template>
  <div>
    <div v-if="props.model.report.reportSummary">
      <q-markup-table bordered flat>
        <thead>
          <tr>
            <th scope="col">Change</th>
            <th scope="col">Property</th>
            <th scope="col">New</th>
            <th scope="col">Original</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(v, k) in props.model.report.changes" :key="k" :class="v['breaking'] ? 'text-negative' : ''">
            <td>{{ v['changeText'] }}{{ v['breaking'] ? '❌' : '' }}</td>
            <td>{{ v['property'] }}</td>
            <td>{{ v['new'] }}</td>
            <td>{{ v['original'] }}</td>
          </tr>
        </tbody>
      </q-markup-table>
      <!-- TODO WORKAROUND FOR https://github.com/pb33f/openapi-changes/issues/137 and https://github.com/quasarframework/quasar-ui-qmarkdown/issues/394 -->
      <q-markdown :src="props.model.reportPaths" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import type IReportDiff from 'src/model/document/IReportDiff'

const props = defineProps<{ model: IReportDiff }>()
</script>
