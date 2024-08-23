import AEntity from 'src/model/AEntity'
import EType from 'src/model/enums/EType'

export default class Update extends AEntity {
  url!: string
  type: EType = EType.GIT
}
