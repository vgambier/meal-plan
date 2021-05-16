import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './meal-plan.reducer';
import { getEntities as getRecipeServingEntities } from 'app/entities/recipe-serving/recipe-serving.reducer';
import { getEntities as getRecipeEntities } from 'app/entities/recipe/recipe.reducer';

export interface IMealPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MealPlanDetail = (props: IMealPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
    props.getRecipeServingEntities();
    props.getRecipeEntities();
  }, []);

  const { mealPlanEntity, recipeServings, recipes } = props;

  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mealPlanDetailsHeading">Meal Plan {mealPlanEntity.name}</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="recipes">Recipes</span>
          </dt>
          <dd>
            {recipes.length > 0 &&
              recipeServings
                .filter(recipe => recipe.mealPlan.id === mealPlanEntity.id)
                .map(recipe => recipe.recipe.id)
                .map(id => <dd key={id}>{recipes.find(recipe => recipe.id === id).name}</dd>)}
          </dd>
        </dl>
        <Button tag={Link} to="/meal-plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meal-plan/${mealPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  mealPlanEntity: storeState.mealPlan.entity,
  recipeServings: storeState.recipeServing.entities,
  recipes: storeState.recipe.entities,
});

const mapDispatchToProps = {
  getEntity,
  getRecipeServingEntities,
  getRecipeEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MealPlanDetail);
