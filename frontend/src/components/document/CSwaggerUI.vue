<template>
  <div id="swagger-ui" />
</template>

<script setup lang="ts">
import SwaggerUI from 'swagger-ui'
import YAML from 'yaml'
import { computed, onMounted } from 'vue'

const props = defineProps<{ model: string }>()

const specYaml = computed(() => YAML.parse(props.model) as Record<string, never>)

onMounted(() => {
  // Just ignore the developer console error 'You are importing createRoot from "react-dom" which is not supported. You should instead import it from "react-dom/client".'
  //  See https://github.com/swagger-api/swagger-ui/issues/9865
  // TODO "missing vue component" Workaround for https://github.com/swagger-api/swagger-ui/issues/7351
  SwaggerUI({
    dom_id: '#swagger-ui',
    spec: specYaml.value,
  })
})
</script>

<style lang="scss">
// TODO Workaround until https://github.com/quasarframework/quasar/discussions/15467 got resolved
#swagger-ui {
  h2 {
    font-weight: bold;
    line-height: normal;
  }
  h3 {
    font-weight: bold;
    line-height: normal;
  }
  h4 {
    font-weight: bold;
    line-height: normal;
  }
  .parameters-col_description {
    .markdown {
      p {
        font-size: 20px;
      }
    }
  }
  .scheme-container {
    .schemes {
      display: flex !important;
    }
  }
}
@import 'swagger-ui/dist/swagger-ui.css';
</style>
