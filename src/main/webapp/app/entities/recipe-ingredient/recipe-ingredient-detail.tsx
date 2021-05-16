import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recipe-ingredient.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipeIngredientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipeIngredientDetail = (props: IRecipeIngredientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recipeIngredientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recipeIngredientDetailsHeading">RecipeIngredient</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{recipeIngredientEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{recipeIngredientEntity.quantity}</dd>
          <dt>
            <span id="unit">Unit</span>
          </dt>
          <dd>{recipeIngredientEntity.unit}</dd>
          <dt>
            <span id="optional">Optional</span>
          </dt>
          <dd>{recipeIngredientEntity.optional ? 'true' : 'false'}</dd>
          <dt>Ingredient</dt>
          <dd>{recipeIngredientEntity.ingredient ? recipeIngredientEntity.ingredient.id : ''}</dd>
          <dt>Recipe</dt>
          <dd>{recipeIngredientEntity.recipe ? recipeIngredientEntity.recipe.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/recipe-ingredient" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recipe-ingredient/${recipeIngredientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ recipeIngredient }: IRootState) => ({
  recipeIngredientEntity: recipeIngredient.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeIngredientDetail);
