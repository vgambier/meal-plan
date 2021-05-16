import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRecipe } from 'app/shared/model/recipe.model';
import { getEntities as getRecipes } from 'app/entities/recipe/recipe.reducer';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { getEntities as getMealPlans } from 'app/entities/meal-plan/meal-plan.reducer';
import { getEntity, updateEntity, createEntity, reset } from './recipe-serving.reducer';
import { IRecipeServing } from 'app/shared/model/recipe-serving.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecipeServingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipeServingUpdate = (props: IRecipeServingUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { recipeServingEntity, recipes, mealPlans, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/recipe-serving');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRecipes();
    props.getMealPlans();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...recipeServingEntity,
        ...values,
        recipe: recipes.find(it => it.id.toString() === values.recipeId.toString()),
        mealPlan: mealPlans.find(it => it.id.toString() === values.mealPlanId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mealPlanApp.recipeServing.home.createOrEditLabel" data-cy="RecipeServingCreateUpdateHeading">
            Create or edit a RecipeServing
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recipeServingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="recipe-serving-id">ID</Label>
                  <AvInput id="recipe-serving-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="servingsOverrideLabel" for="recipe-serving-servingsOverride">
                  Servings Override
                </Label>
                <AvField
                  id="recipe-serving-servingsOverride"
                  data-cy="servingsOverride"
                  type="string"
                  className="form-control"
                  name="servingsOverride"
                />
              </AvGroup>
              <AvGroup>
                <Label for="recipe-serving-recipe">Recipe</Label>
                <AvInput id="recipe-serving-recipe" data-cy="recipe" type="select" className="form-control" name="recipeId" required>
                  <option value="" key="0" />
                  {recipes
                    ? recipes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="recipe-serving-mealPlan">Meal Plan</Label>
                <AvInput id="recipe-serving-mealPlan" data-cy="mealPlan" type="select" className="form-control" name="mealPlanId">
                  <option value="" key="0" />
                  {mealPlans
                    ? mealPlans.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/recipe-serving" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  recipes: storeState.recipe.entities,
  mealPlans: storeState.mealPlan.entities,
  recipeServingEntity: storeState.recipeServing.entity,
  loading: storeState.recipeServing.loading,
  updating: storeState.recipeServing.updating,
  updateSuccess: storeState.recipeServing.updateSuccess,
});

const mapDispatchToProps = {
  getRecipes,
  getMealPlans,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeServingUpdate);
