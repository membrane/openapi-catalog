import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    children: [
      { path: '/', redirect: { name: 'specification-list' } },
      {
        children: [{ component: () => import('pages/document/DetailsPage.vue'), name: 'document-details', path: ':id', props: true }],
        path: 'documents',
      },
      {
        children: [
          { component: () => import('pages/specification/ChangelogPage.vue'), name: 'specification-changelog', path: ':id/changelog/:version', props: true },
          { component: () => import('pages/specification/DetailsPage.vue'), name: 'specification-details', path: ':id', props: true },
          { component: () => import('pages/specification/ListPage.vue'), name: 'specification-list', path: '' },
        ],
        path: 'specifications',
      },
    ],
    component: () => import('layouts/MainLayout.vue'),
    path: '/',
  },

  // Always leave this as the last one, but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
]

export default routes
