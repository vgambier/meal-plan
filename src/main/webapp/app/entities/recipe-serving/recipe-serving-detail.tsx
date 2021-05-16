import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recipe-serving.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipeServingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipeServingDetail = (props: IRecipeServingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recipeServingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recipeServingDetailsHeading">RecipeServing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{recipeServingEntity.id}</dd>
          <dt>
            <span id="servingsOverride">Servings Override</span>
          </dt>
          <dd>{recipeServingEntity.servingsOverride}</dd>
          <dt>Recipe</dt>
          <dd>{recipeServingEntity.recipe ? recipeServingEntity.recipe.id : ''}</dd>
          <dt>Meal Plan</dt>
          <dd>{recipeServingEntity.mealPlan ? recipeServingEntity.mealPlan.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/recipe-serving" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recipe-serving/${recipeServingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ recipeServing }: IRootState) => ({
  recipeServingEntity: recipeServing.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeServingDetail);
