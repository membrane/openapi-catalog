import AEntity from 'src/model/AEntity'
import URL from 'src/model/URL'

export default class Update extends AEntity {
  interval = 30
  path?: string
  url: URL = new URL()
}
