<template>
  <router-view />
</template>

<style lang="scss">
// TODO WORKAROUND FOR https://github.com/quasarframework/quasar/discussions/17203
@import 'quasar/dist/quasar.sass';

.q-table {
  thead {
    tr {
      // TODO WORKAROUND FOR https://github.com/quasarframework/quasar/discussions/17203
      @extend .bg-grey-3;
      th {
        // TODO WORKAROUND FOR https://github.com/quasarframework/quasar/discussions/17203
        font-weight: bold;
        text-align: left;
      }
    }
  }
  tbody {
    tr {
      td {
        @extend .vertical-top;
      }
    }
  }
}
</style>
