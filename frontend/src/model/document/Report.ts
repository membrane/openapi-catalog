import AEntity from 'src/model/AEntity'
import type IReportDiff from 'src/model/document/IReportDiff'
import type LintReport from 'src/model/document/LintReport'

export default class Report extends AEntity {
  diffReport?: IReportDiff
  lintReports: LintReport[] = []
}
