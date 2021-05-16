import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MealPlan from './meal-plan';
import MealPlanDetail from './meal-plan-detail';
import MealPlanUpdate from './meal-plan-update';
import MealPlanDeleteDialog from './meal-plan-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MealPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MealPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MealPlanDetail} />
      <ErrorBoundaryRoute path={match.url} component={MealPlan} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MealPlanDeleteDialog} />
  </>
);

export default Routes;
