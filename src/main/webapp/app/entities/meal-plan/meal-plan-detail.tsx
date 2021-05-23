import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './meal-plan.reducer';

export interface IMealPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MealPlanDetail = (props: IMealPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mealPlanEntity } = props;

  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mealPlanDetailsHeading">Meal Plan {mealPlanEntity.name}</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="recipes">Recipes</span>
          </dt>
          {mealPlanEntity?.recipes?.length > 0 &&
            mealPlanEntity?.recipes?.map(recipe => <dd key={recipe.recipe.id}>{recipe.recipe.name}</dd>)}
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

const mapStateToProps = ({ mealPlan }: IRootState) => ({
  mealPlanEntity: mealPlan.entity,
});

const mapDispatchToProps = {
  getEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MealPlanDetail);
