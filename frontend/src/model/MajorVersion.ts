import AEntity from 'src/model/AEntity'
import type Document from 'src/model/Document'
import type LintRule from 'src/model/majorVersion/LintRule'
import type Specification from 'src/model/Specification'

export default class MajorVersion extends AEntity {
  documents: Document[] = []
  lintRules: LintRule[] = []
  specification!: Specification
  version!: number
}
