<template>
  <div>
    <q-btn color="primary" :icon="mdiPlusCircleOutline" label="New specification" :loading @click="dialog = true" />
    <q-dialog v-model="dialog" persistent @hide="cleanup">
      <q-card style="min-width: 40em">
        <q-card-section>
          <q-tabs v-model="tab" align="justify" indicator-color="primary">
            <q-tab name="automatic" label="Automatic" />
            <q-tab name="manually" label="Manually" />
          </q-tabs>
          <q-separator />
          <q-tab-panels v-model="tab">
            <q-tab-panel name="automatic">
              <q-input v-model="update.url.url" label="Source (URL)" :loading type="url">
                <template #prepend>
                  <q-icon :name="mdiLink" />
                </template>
              </q-input>
              <q-input v-model="update.path" label="GIT-Path" :loading type="text">
                <template #prepend>
                  <q-icon :name="mdiFile" />
                </template>
              </q-input>
              <q-input v-model="update.interval" label="Scan intervall (Seconds)" :loading type="number">
                <template #prepend>
                  <q-icon :name="mdiProgressClock" />
                </template>
              </q-input>
            </q-tab-panel>
            <q-tab-panel name="manually">
              <q-file v-model="file" accept=".yaml" counter label="Specification.yaml" :loading outlined>
                <template #before>
                  <q-icon :name="mdiAttachment" />
                </template>
                <template #append>
                  <q-icon v-if="file !== null" class="cursor-pointer" :name="mdiClose" @click.stop.prevent="file = null" />
                </template>
              </q-file>
            </q-tab-panel>
          </q-tab-panels>
        </q-card-section>
        <q-card-actions align="around">
          <q-btn color="primary" flat label="Cancel" :loading />
          <q-btn color="primary" label="Upload" :loading @click="upload" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { mdiAttachment, mdiClose, mdiFile, mdiLink, mdiPlusCircleOutline, mdiProgressClock } from '@quasar/extras/mdi-v7'
import Document from 'src/model/Document'
import SpecificationTmp from 'src/model/SpecificationTmp'
import EType from 'src/model/enums/EType'
import Update from 'src/model/specification/Update'
import { useFetch } from 'src/utils/Fetch'

const emit = defineEmits<{ save: [value: boolean] }>()

const fetch = useFetch()

const dialog = ref(false)
const file = ref<File | null | undefined>()
const loading = ref(false)
const update = ref(new Update())
const specification = ref(new SpecificationTmp())
const tab = ref('automatic')

const cleanup = () => {
  file.value = null
  specification.value = new SpecificationTmp()
}

const upload = async () => {
  const document = new Document()

  if (update.value.url.url) {
    if (/^.*\.(yaml|yml)$/gi.test(update.value.url.url)) update.value.url.type = EType.HTTP

    specification.value.update = update.value
  } else
    await file.value?.text().then((text) => {
      document.content = text

      specification.value.documents.push(document)
    })

  if (update.value.url.url || specification.value.documents.length > 0) {
    loading.value = true

    await fetch.post(specification.value, 'specifications').then(() => {
      emit('save', true)
      loading.value = false
      dialog.value = false
    })
  }
}
</script>
