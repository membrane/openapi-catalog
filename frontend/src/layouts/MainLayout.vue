<template>
  <q-layout view="hHh Lpr fFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn aria-label="Menu" dense flat :icon="mdiMenu" round @click="leftDrawerOpen = !leftDrawerOpen" />
        <q-toolbar-title>OpenAPI Catalog</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item :to="{ name: 'specification-list' }" exact>
          <q-item-section avatar>
            <q-icon :name="mdiApi" />
          </q-item-section>
          <q-item-section>
            <q-item-label>Specifications</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>
    <q-page-container>
      <q-page>
        <router-view />
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { mdiApi, mdiMenu } from '@quasar/extras/mdi-v7'

const leftDrawerOpen = ref(false)
</script>
