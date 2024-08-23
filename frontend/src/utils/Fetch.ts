export function useFetch() {
  const port = process.env['DEV'] ? '9091' : '8000'
  const baseUrl = `http://localhost:${port}/api/v1/`

  const get = async (endpoint: string) =>
    await fetch(baseUrl + endpoint, {
      headers: {
        'Content-Type': 'application/json',
      },
    })

  const post = async (data: unknown, endpoint: string) => await postOrPut(data, endpoint, 'POST')

  const put = async (data: unknown, endpoint: string) => await postOrPut(data, endpoint, 'PUT')

  const postOrPut = async (data: unknown, endpoint: string, method: 'POST' | 'PUT') =>
    await fetch(baseUrl + endpoint, {
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json',
      },
      method: method,
    })

  return { baseUrl, get, post, put }
}
