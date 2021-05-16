import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IIngredient } from 'app/shared/model/ingredient.model';
import { getEntities as getIngredients } from 'app/entities/ingredient/ingredient.reducer';
import { IRecipe } from 'app/shared/model/recipe.model';
import { getEntities as getRecipes } from 'app/entities/recipe/recipe.reducer';
import { getEntity, updateEntity, createEntity, reset } from './recipe-ingredient.reducer';
import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecipeIngredientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipeIngredientUpdate = (props: IRecipeIngredientUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { recipeIngredientEntity, ingredients, recipes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/recipe-ingredient');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getIngredients();
    props.getRecipes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...recipeIngredientEntity,
        ...values,
        ingredient: ingredients.find(it => it.id.toString() === values.ingredientId.toString()),
        recipe: recipes.find(it => it.id.toString() === values.recipeId.toString()),
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
          <h2 id="mealPlanApp.recipeIngredient.home.createOrEditLabel" data-cy="RecipeIngredientCreateUpdateHeading">
            Create or edit a RecipeIngredient
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recipeIngredientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="recipe-ingredient-id">ID</Label>
                  <AvInput id="recipe-ingredient-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="quantityLabel" for="recipe-ingredient-quantity">
                  Quantity
                </Label>
                <AvField id="recipe-ingredient-quantity" data-cy="quantity" type="string" className="form-control" name="quantity" />
              </AvGroup>
              <AvGroup>
                <Label id="unitLabel" for="recipe-ingredient-unit">
                  Unit
                </Label>
                <AvField id="recipe-ingredient-unit" data-cy="unit" type="text" name="unit" />
              </AvGroup>
              <AvGroup check>
                <Label id="optionalLabel">
                  <AvInput
                    id="recipe-ingredient-optional"
                    data-cy="optional"
                    type="checkbox"
                    className="form-check-input"
                    name="optional"
                  />
                  Optional
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="recipe-ingredient-ingredient">Ingredient</Label>
                <AvInput
                  id="recipe-ingredient-ingredient"
                  data-cy="ingredient"
                  type="select"
                  className="form-control"
                  name="ingredientId"
                  required
                >
                  <option value="" key="0" />
                  {ingredients
                    ? ingredients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="recipe-ingredient-recipe">Recipe</Label>
                <AvInput id="recipe-ingredient-recipe" data-cy="recipe" type="select" className="form-control" name="recipeId">
                  <option value="" key="0" />
                  {recipes
                    ? recipes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/recipe-ingredient" replace color="info">
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
  ingredients: storeState.ingredient.entities,
  recipes: storeState.recipe.entities,
  recipeIngredientEntity: storeState.recipeIngredient.entity,
  loading: storeState.recipeIngredient.loading,
  updating: storeState.recipeIngredient.updating,
  updateSuccess: storeState.recipeIngredient.updateSuccess,
});

const mapDispatchToProps = {
  getIngredients,
  getRecipes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeIngredientUpdate);
