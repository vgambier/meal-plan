import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, Container } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './meal-plan.reducer';
import { createEntity as createRecipeServingEntity } from 'app/entities/recipe-serving/recipe-serving.reducer';
import { getEntities as getRecipes } from 'app/entities/recipe/recipe.reducer';

export interface IMealPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MealPlanUpdate = (props: IMealPlanUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const [mealPlanRecipes, setMealPlanRecipes] = useState([]);

  const { mealPlanEntity, recipes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/meal-plan');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
    props.getRecipes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (_event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...mealPlanEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }

      mealPlanRecipes.map(formRecipe => {
        const recipeServingEntity = {
          recipe: recipes.find(it => it.id.toString() === formRecipe.recipeId.toString()),
          servingsOverride: formRecipe.servingsOverride,
          mealPlan: entity,
        };

        props.createRecipeServingEntity(recipeServingEntity);
      });
    }
  };

  const handleRecipeIdChange = idx => evt => {
    const newMealPlanRecipes = mealPlanRecipes.map((recipe, sidx) => {
      if (idx !== sidx) return recipe;
      return { ...recipe, recipeId: evt.target.value };
    });

    setMealPlanRecipes(newMealPlanRecipes);
  };

  const handleRecipeServingsChange = idx => evt => {
    const newMealPlanRecipes = mealPlanRecipes.map((recipe, sidx) => {
      if (idx !== sidx) return recipe;
      return { ...recipe, servingsOverride: evt.target.value };
    });

    setMealPlanRecipes(newMealPlanRecipes);
  };

  const handleAddRecipe = () => {
    setMealPlanRecipes(mealPlanRecipes.concat([{ recipeId: '', servingsOverride: '' }]));
  };

  const handleRemoveRecipe = idx => () => {
    setMealPlanRecipes(mealPlanRecipes.filter((_s, sidx) => idx !== sidx));
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mealPlanApp.mealPlan.home.createOrEditLabel" data-cy="MealPlanCreateUpdateHeading">
            Create or edit a meal plan
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mealPlanEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="meal-plan-id">ID</Label>
                  <AvInput id="meal-plan-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="meal-plan-name">
                  Name
                </Label>
                <AvField
                  id="meal-plan-name"
                  data-cy="name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <Label for="meal-plan-recipes">Recipes</Label>
              <br />
              {mealPlanRecipes.map((_mealPlanRecipe, idx) => (
                <div key={idx} className="mealPlanRecipe">
                  <Container>
                    <Row>
                      <AvGroup>
                        <AvInput
                          form="fakeform"
                          id="recipe-serving-recipe"
                          data-cy="recipe"
                          type="select"
                          className="form-control"
                          name={'recipeId' + (idx + 1)}
                          required // TODO make it not required but make sure it is ignored if empty
                          onChange={handleRecipeIdChange(idx)}
                        >
                          <option value="" key="0" />
                          {recipes
                            ? recipes.map(otherEntity => (
                                <option value={otherEntity.id} key={otherEntity.id}>
                                  {otherEntity.name}
                                </option> // TODO hide recipes already present in other forms
                              ))
                            : null}
                        </AvInput>
                        <AvFeedback>This field is required.</AvFeedback>
                      </AvGroup>
                      <Col sm="2">
                        <AvGroup>
                          <AvField
                            id="recipe-serving-servingsOverride"
                            data-cy="servingsOverride"
                            type="number"
                            placeholder="Servings"
                            className="form-control"
                            value={recipes.find(recipe => recipe.id === parseInt(mealPlanRecipes[idx]?.recipeId, 10))?.servings}
                            name={'servingsOverride' + (idx + 1)}
                            onChange={handleRecipeServingsChange(idx)}
                          />
                        </AvGroup>
                      </Col>
                      <Col sm="1">
                        <Button
                          type="button"
                          onClick={
                            handleRemoveRecipe(idx) // TODO this always deletes the last one
                          }
                        >
                          -
                        </Button>
                      </Col>
                    </Row>
                  </Container>
                </div>
              ))}
              <Button type="button" onClick={handleAddRecipe} className="small">
                Add recipe
              </Button>
              <br /> <br />
              <Button tag={Link} id="cancel-save" to="/meal-plan" replace color="info">
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
  mealPlanEntity: storeState.mealPlan.entity,
  recipes: storeState.recipe.entities,
  loading: storeState.mealPlan.loading,
  updating: storeState.mealPlan.updating,
  updateSuccess: storeState.mealPlan.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  getRecipes,
  updateEntity,
  createEntity,
  createRecipeServingEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MealPlanUpdate);
