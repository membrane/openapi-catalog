import AEntity from 'src/model/AEntity'
import type Document from 'src/model/Document'
import type Update from 'src/model/specification/Update'

export default class SpecificationTmp extends AEntity {
  documents: Document[] = []
  update?: Update
}
