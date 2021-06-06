import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, Container } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './recipe.reducer';
import { getEntities as getIngredients } from 'app/entities/ingredient/ingredient.reducer';

export interface IRecipeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipeUpdate = (props: IRecipeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const [recipeIngredients, setRecipeIngredients] = useState([]);

  const { recipeEntity, ingredients, loading, updating } = props;

  const { instructions, additionalNotes, picture, pictureContentType } = recipeEntity;

  const handleClose = () => {
    props.history.push('/recipe');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
    props.getIngredients();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (_event, errors, values) => {
    if (errors.length === 0) {
      const recipeIngredientEntities = recipeIngredients.map(formIngredient => {
        return {
          ingredient: ingredients.find(it => it.id.toString() === formIngredient.ingredientId.toString()),
          quantity: formIngredient.quantity,
          unit: formIngredient.unit,
          optional: formIngredient.optional,
        };
      });

      const entity = {
        ...recipeEntity,
        ...values,
        ingredients: recipeIngredientEntities,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  const handleIngredientIdChange = idx => evt => {
    const newRecipeIngredients = recipeIngredients.map((ingredient, sidx) => {
      if (idx !== sidx) return ingredient;
      return {
        ...ingredient,
        ingredientId: evt.target.value,
      };
    });

    setRecipeIngredients(newRecipeIngredients);
  };

  // TODO all 3 functions below could be unified

  const handleIngredientQuantityChange = idx => evt => {
    const newRecipeIngredients = recipeIngredients.map((ingredient, sidx) => {
      if (idx !== sidx) return ingredient;
      return { ...ingredient, quantity: evt.target.value };
    });

    setRecipeIngredients(newRecipeIngredients);
  };

  const handleIngredientUnitChange = idx => evt => {
    const newRecipeIngredients = recipeIngredients.map((ingredient, sidx) => {
      if (idx !== sidx) return ingredient;
      return { ...ingredient, unit: evt.target.value };
    });

    setRecipeIngredients(newRecipeIngredients);
  };

  const handleIngredientOptionalChange = idx => evt => {
    const newRecipeIngredients = recipeIngredients.map((ingredient, sidx) => {
      if (idx !== sidx) return ingredient;
      return { ...ingredient, optional: evt.target.value };
    });

    setRecipeIngredients(newRecipeIngredients);
  };

  const handleAddIngredient = () => {
    setRecipeIngredients(recipeIngredients.concat([{ ingredientId: '', quantity: '', unit: '', optional: 'false' }]));
  };

  const handleRemoveIngredient = idx => () => {
    setRecipeIngredients(recipeIngredients.filter((_s, sidx) => idx !== sidx));
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mealPlanApp.recipe.home.createOrEditLabel" data-cy="RecipeCreateUpdateHeading">
            Create or edit a recipe
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recipeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="recipe-id">ID</Label>
                  <AvInput id="recipe-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="recipe-name">
                  Name
                </Label>
                <AvField
                  id="recipe-name"
                  data-cy="name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="servingsLabel" for="recipe-servings">
                  Servings
                </Label>
                <AvField
                  id="recipe-servings"
                  data-cy="servings"
                  type="string"
                  className="form-control"
                  name="servings"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <Label for="recipe-ingredients">Ingredients</Label>
              <br />
              {recipeIngredients.map((_recipeIngredient, idx) => (
                <div key={idx} className="recipeIngredient">
                  <Container>
                    <Row>
                      <AvGroup>
                        <AvInput
                          form="fakeform"
                          id="recipe-ingredient-ingredient"
                          data-cy="ingredient"
                          type="select"
                          className="form-control"
                          name={'ingredientId' + (idx + 1)}
                          required // TODO make it not required but make sure it is ignored if empty
                          onChange={handleIngredientIdChange(idx)}
                        >
                          <option value="" key="0" />
                          {ingredients // TODO this should not be a dropdown but a free input field with dynamic suggestions
                            ? ingredients.map(otherEntity => (
                                <option value={otherEntity.id} key={otherEntity.id}>
                                  {otherEntity.name}
                                </option> // TODO hide ingredients already present in other forms
                              ))
                            : null}
                        </AvInput>
                        <AvFeedback>This field is required.</AvFeedback>
                      </AvGroup>
                      <Col sm="2">
                        <AvGroup>
                          <AvField
                            id="recipe-ingredient-quantity"
                            data-cy="quantity"
                            type="number"
                            placeholder="Quantity"
                            className="form-control"
                            // TODO also unit and optional way below or sthg
                            name={'quantity' + (idx + 1)}
                            onChange={handleIngredientQuantityChange(idx)}
                          />
                        </AvGroup>
                      </Col>
                      <Col sm="2">
                        <AvGroup>
                          <AvField
                            id="recipe-ingredient-unit"
                            data-cy="unit"
                            type="string"
                            placeholder="Unit"
                            className="form-control"
                            name={'unit' + (idx + 1)}
                            onChange={handleIngredientUnitChange(idx)}
                          />
                        </AvGroup>
                      </Col>
                      <Col sm="2">
                        <AvGroup>
                          <AvField
                            id="recipe-ingredient-optional"
                            data-cy="optional"
                            type="boolean"
                            placeholder="Optional"
                            className="form-control"
                            name={'optional' + (idx + 1)}
                            onChange={
                              handleIngredientOptionalChange(idx) // TODO this field should be a checkbox
                            }
                          />
                        </AvGroup>
                      </Col>
                      <Col sm="1">
                        <Button
                          type="button"
                          onClick={
                            handleRemoveIngredient(idx) // TODO this always deletes the last one
                          }
                        >
                          -
                        </Button>
                      </Col>
                    </Row>
                  </Container>
                </div>
              ))}
              <Button type="button" onClick={handleAddIngredient} className="small">
                Add ingredient
              </Button>
              <br /> <br />
              <AvGroup>
                <Label id="instructionsLabel" for="recipe-instructions">
                  Instructions
                </Label>
                <AvInput
                  id="recipe-instructions"
                  data-cy="instructions"
                  type="textarea"
                  name="instructions"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="additionalNotesLabel" for="recipe-additionalNotes">
                  Additional Notes
                </Label>
                <AvInput id="recipe-additionalNotes" data-cy="additionalNotes" type="textarea" name="additionalNotes" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="pictureLabel" for="picture">
                    Picture
                  </Label>
                  <br />
                  {picture ? (
                    <div>
                      {pictureContentType ? (
                        <a onClick={openFile(pictureContentType, picture)}>
                          <img src={`data:${pictureContentType};base64,${picture}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {pictureContentType}, {byteSize(picture)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('picture')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_picture" data-cy="picture" type="file" onChange={onBlobChange(true, 'picture')} accept="image/*" />
                  <AvInput type="hidden" name="picture" value={picture} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="sourceLabel" for="recipe-source">
                  Source
                </Label>
                <AvField id="recipe-source" data-cy="source" type="text" name="source" />
              </AvGroup>
              <AvGroup>
                <Label id="seasonLabel" for="recipe-season">
                  Season
                </Label>
                <AvInput
                  id="recipe-season"
                  data-cy="season"
                  type="select"
                  className="form-control"
                  name="season"
                  value={(!isNew && recipeEntity.season) || 'WINTER'}
                >
                  <option value="WINTER">WINTER</option>
                  <option value="SUMMER">SUMMER</option>
                  <option value="YEAR_ROUND">YEAR_ROUND</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/recipe" replace color="info">
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
  recipeEntity: storeState.recipe.entity,
  ingredients: storeState.ingredient.entities,
  loading: storeState.recipe.loading,
  updating: storeState.recipe.updating,
  updateSuccess: storeState.recipe.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  getIngredients,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeUpdate);
