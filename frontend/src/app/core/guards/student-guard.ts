import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';

export const studentGuard: CanActivateFn = (route, state) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  if(authService.getToken() && authService.getUserRole()==='STUDENT'){
    return true;
  }

  router.navigate(['/login']);

  return false;
};
