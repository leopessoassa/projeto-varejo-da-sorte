import {  UseQueryOptions, useQuery } from "react-query";
import apiClient from "../http-common";
import { IClient } from "../interfaces/IClient";
import { AxiosError } from "axios";

namespace ClientService {
  export function useGetClients(
    options?: UseQueryOptions<IClient[], AxiosError>
  ) {
    const queryFunction = async () => {
      try {
        const resp = await apiClient.get("/clients");
        return resp.data;
      } catch (e) {
        throw e;
      }
    };
    return useQuery<IClient[], AxiosError>(
      "use-get-clients",
      queryFunction,
      options
    );
  }
}
export default ClientService;
