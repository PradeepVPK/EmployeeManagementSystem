// auth.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Employee } from '../employee';
import { Router } from '@angular/router';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  isLogin: boolean = true; // True: Login Form, False: Registration Form
  loginForm!: FormGroup;
  registrationForm!: FormGroup;
  firstName!: string;
  emailId!: string;
  employee:Employee=new Employee();

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    this.registrationForm = this.formBuilder.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      emailId: ['', [Validators.required, Validators.email]]
      // Add other form controls and validators for registration
      // ...
    });
  }

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  onLoginSubmit() {
    const headers = new HttpHeaders()
      .set('firstName', this.firstName)
      .set('emailId', this.emailId);

    this.employeeService.employeeLogin(headers).subscribe({
      next: (data) => {
       if(data!=null)
        this.goToEmployeeList();
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onRegistrationSubmit() {
    if (this.registrationForm.invalid) {
      this.registrationForm.markAllAsTouched();
      return;
    }
    this.saveEmployee();

  }
  
  saveEmployee() {
    this.employeeService.createEmployee(this.employee).subscribe({
      next: (data) => {
        if(data!=null)
        this.goToEmployeeList();
      },
      error: (error) => {
        console.log(error);
      }
    });
   
  }
  

  goToEmployeeList() {
    this.router.navigate(['/employees']);
  }

  onGoogleLogin() {
    // Perform Google login logic here
    alert('Login with Google clicked');
  }

  toggleForm(event :Event) {
    event.preventDefault();
    this.isLogin = !this.isLogin;
  }

}
