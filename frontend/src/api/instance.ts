import axios, {AxiosInstance} from "axios";

export const instance = (jwt: string) => {
    return axios.create({
        baseURL: "http://localhost:8888",
        timeout: 1000,
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Bearer ' + jwt
        }
      }
    );
}


// export const instance = axios.create({
//     baseURL: "http://localhost:8888",
//     timeout: 1000,
//     headers: {
//         "Content-Type": "application/json",
//         'Authorization': 'Bearer ' + getJwt()
//     }
//   }
// );

function parseCookies(): { [key: string]: string } {
  const cookiesString = document.cookie;
  const cookies: string[] = cookiesString.split(';');
  const result: { [key: string]: string } = {};

  cookies.forEach((cookie) => {
    const [key, value] = cookie.trim().split('=');
    result[key] = decodeURIComponent(value);
  });

  return result;
}



export function getJwt(): string {
  const cookies = parseCookies();
  return ('jwt' in cookies)? cookies['jwt'] : "";
}

export function getSubject(): string | null {
  const cookies = parseCookies();
  return ('subject' in cookies)?cookies['subject'] : null; 
}

export function getUsername() : string{
    const subject = getSubject();
    if (subject === null) {
        return 'A';
    }
    return subject;
}
