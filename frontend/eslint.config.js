import js from '@eslint/js'
import globals from 'globals'
import eslintPluginPrettier from 'eslint-plugin-prettier/recommended'
import tslint from 'typescript-eslint'
import vuePlugin from 'eslint-plugin-vue'

export default tslint.config(
  /*
   * Rules order is important, please avoid shuffling them
   * Base ESLint recommended rules
   */
  // eslint-disable-next-line @typescript-eslint/no-unsafe-argument,@typescript-eslint/no-unsafe-member-access
  js.configs.recommended,
  /*
   * https://github.com/typescript-eslint/typescript-eslint/tree/master/packages/eslint-plugin#usage
   * ESLint typescript rules
   */
  ...tslint.configs.strictTypeChecked,
  ...tslint.configs.stylisticTypeChecked,
  // See https://eslint.vuejs.org/rules/#available-rules
  // eslint-disable-next-line @typescript-eslint/no-unsafe-argument,@typescript-eslint/no-unsafe-member-access
  ...vuePlugin.configs['flat/recommended'],
  /*
   * https://github.com/prettier/eslint-config-prettier#installation
   * usage with Prettier, provided by 'eslint-config-prettier'.
   */
  eslintPluginPrettier,
  { ignores: ['.quasar/**', 'dist/**', 'node_modules/**'] },
  {
    languageOptions: {
      // global variables
      globals: {
        ...globals.browser,
        ...globals.es2025,
        ...globals.nodeBuiltin,
        Capacitor: 'readonly',
        __QUASAR_SSR_CLIENT__: 'readonly',
        __QUASAR_SSR_PWA__: 'readonly',
        __QUASAR_SSR_SERVER__: 'readonly',
        __QUASAR_SSR__: 'readonly',
        __statics: 'readonly',
        chrome: 'readonly',
        cordova: 'readonly',
        // Google Analytics
        ga: 'readonly',
        process: 'readonly',
      },
      /*
       * https://eslint.vuejs.org/user-guide/#how-to-use-a-custom-parser
       * Must use parserOptions instead of "parser" to allow vue-eslint-parser to keep working
       * `parser: 'vue-eslint-parser'` is already included with any 'plugin:vue/**' config and should be omitted
       */
      parserOptions: {
        ecmaVersion: 'latest',
        extraFileExtensions: ['.vue'],
        parser: tslint.parser,
        project: true,
        sourceType: 'module',
      },
    },
    // Add your custom rules here
    rules: {},
  },
)
