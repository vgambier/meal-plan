import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ingredient from './ingredient';
import Recipe from './recipe';
import RecipeIngredient from './recipe-ingredient';
import MealPlan from './meal-plan';
import RecipeServing from './recipe-serving';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}ingredient`} component={Ingredient} />
      <ErrorBoundaryRoute path={`${match.url}recipe`} component={Recipe} />
      <ErrorBoundaryRoute path={`${match.url}recipe-ingredient`} component={RecipeIngredient} />
      <ErrorBoundaryRoute path={`${match.url}meal-plan`} component={MealPlan} />
      <ErrorBoundaryRoute path={`${match.url}recipe-serving`} component={RecipeServing} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
