import AEntity from 'src/model/AEntity'
import type MajorVersion from 'src/model/MajorVersion'
import type Report from 'src/model/document/Report'

export default class Document extends AEntity {
  content!: string
  majorVersion!: MajorVersion
  openapiVersion!: string
  report!: Report
  servers!: Map<string, string>
  timestamp!: Date
  title!: string
  version!: string
}
