export default interface IReportLintEntry {
  code: string
  message: string
  path: string[]
  range: {
    end: {
      character: number
      line: number
    }
    start: {
      character: number
      line: number
    }
  }
  severity: number
}
