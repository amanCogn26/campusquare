import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { LoaderService } from '../services/loader.service';
import { finalize } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const loaderService = inject(LoaderService);

  loaderService.show();

  if (req.url.includes('/auth/login') || req.url.includes('/auth/register')) {
    // return next(req);
    return next(req).pipe(
      finalize(() => loaderService.hide())
    );
  }

  const authService = inject(AuthService);
  const token = authService.getToken();
  const role = authService.getUserRole();
  const userId = authService.getUserId();
  const collegeId = authService.getCollegeId();

  const headers: Record<string, string> = {};

  if (token) {
    headers['AUTHORIZATION'] = `Bearer ${token}`;
  }
  if (role) {
    headers['X-User-Role'] = role;
  }
  if (userId) {
    headers['X-User-Id'] = userId.toString();
  }
  if (collegeId) {
    headers['X-College-Id'] = collegeId.toString();
  }

  const cloned = req.clone({ setHeaders: headers });

  loaderService.show();

  return next(cloned).pipe(
    finalize(() => loaderService.hide())
  );

  return next(cloned);
};