import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private loginUrl="http://localhost:8080/api/v1/login";
  private baseUrl="http://localhost:8080/api/v1/employees";

  constructor( private httpClient :HttpClient) { }

  employeeLogin(headers:HttpHeaders):Observable<Employee> {
    return this.httpClient.get<Employee>(`${this.loginUrl}`,{headers});
  }
  getEmployeesList(): Observable<Employee[]>{
    return this.httpClient.get<Employee[]>(`${this.baseUrl}`);
  }

  createEmployee(employee:Employee):Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}`,employee);
  }

  getEmployeeById(id:number):Observable<Employee>{
    return this.httpClient.get<Employee>(`${this.baseUrl}/${id}`);
  }

  updateEmployee(id:number,employee:Employee):Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/${id}`,employee);
  }

  deleteEmployee(id:number):Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
}
}
