import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './recipe.reducer';

export interface IRecipeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipeUpdate = (props: IRecipeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { recipeEntity, loading, updating } = props;

  const { instructions, additionalNotes, picture, pictureContentType } = recipeEntity;

  const handleClose = () => {
    props.history.push('/recipe');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
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

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...recipeEntity,
        ...values,
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
  loading: storeState.recipe.loading,
  updating: storeState.recipe.updating,
  updateSuccess: storeState.recipe.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeUpdate);
