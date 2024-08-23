import AEntity from 'src/model/AEntity'
import type Update from 'src/model/specification/Update'
import type MajorVersion from 'src/model/MajorVersion'

export default class Specification extends AEntity {
  majorVersions: MajorVersion[] = []
  update?: Update
}
