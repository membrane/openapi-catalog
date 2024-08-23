<template>
  <div>
    <q-btn :icon="mdiTextBoxSearchOutline" label="Add Lintingrule" outline @click="dialog = true" />
    <q-dialog v-model="dialog" persistent @hide="cleanup">
      <q-card style="min-width: 40em">
        <q-card-section>
          <q-input v-model="lintRule.name" label="Name" />
          <q-input v-model="lintRule.url.url" label="URL" type="url">
            <template #prepend>
              <q-icon :name="mdiLink" />
            </template>
          </q-input>
        </q-card-section>
        <q-card-actions align="around">
          <q-btn v-close-popup color="primary" flat label="Cancel" />
          <q-btn v-close-popup color="primary" label="Save" @click="upload" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { mdiLink, mdiTextBoxSearchOutline } from '@quasar/extras/mdi-v7'
import LintRule from 'src/model/majorVersion/LintRule'
import { useFetch } from 'src/utils/Fetch'

const emit = defineEmits<{ save: [value: boolean] }>()
const props = defineProps<{ id: string }>()

const fetch = useFetch()

const dialog = ref(false)
const lintRule = ref(new LintRule())

const cleanup = () => (lintRule.value = new LintRule())

const upload = async () => {
  await fetch.post(lintRule.value, `majorversions/${props.id}/lintingrules`).then(() => {
    emit('save', true)
  })
}
</script>
