import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RecipeServing from './recipe-serving';
import RecipeServingDetail from './recipe-serving-detail';
import RecipeServingUpdate from './recipe-serving-update';
import RecipeServingDeleteDialog from './recipe-serving-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RecipeServingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RecipeServingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RecipeServingDetail} />
      <ErrorBoundaryRoute path={match.url} component={RecipeServing} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RecipeServingDeleteDialog} />
  </>
);

export default Routes;
