import AEntity from 'src/model/AEntity'
import type LintReport from 'src/model/document/LintReport'
import type MajorVersion from 'src/model/MajorVersion'
import URL from 'src/model/URL'

export default class LintRule extends AEntity {
  majorVersion?: MajorVersion
  name!: string
  reports: LintReport[] = []
  url = new URL()
}
