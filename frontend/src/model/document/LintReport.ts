import AEntity from 'src/model/AEntity'
import type IReportLintEntry from 'src/model/document/IReportLintEntry'
import type LintRule from 'src/model/majorVersion/LintRule'

export default class LintReport extends AEntity {
  content!: IReportLintEntry[]
  rule?: LintRule
}
