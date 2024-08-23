import type IReportDiffSummary from 'src/model/document/IReportDiffSummary'

export default interface IReportDiff {
  report: {
    changes: Record<string, never>[]
    reportSummary: Record<string, IReportDiffSummary>
  }
  reportPaths: string
}
