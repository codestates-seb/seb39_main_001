import Slider from 'react-slick';
import styled from 'styled-components';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import theme from '../assets/styles/Theme';
import { useState } from 'react';
import useInterval from '../hooks/useInterval';

const Carousel = () => {
  const settings = {
    dots: true,
    speed: 500,
    autoplay: true,
    autoplaySpeed: 6000,
    arrows: false,
    pauseOnHover: true,
  };

  const name = [
    { name: '방현수', github: 'https://github.com/thom-droid' },
    { name: '김은주', github: 'https://github.com/ekim49' },
    { name: '최영민', github: 'https://github.com/aprochoi' },
    { name: '정우용', github: 'https://github.com/cleats01' },
  ];
  const [index, setIndex] = useState(0);

  // 커스텀훅 실행
  useInterval(() => {
    index === name.length - 1 ? setIndex(0) : setIndex(index + 1);
  }, 2000);

  return (
    <CarouselContainer>
      <Slider {...settings}>
        <div>
          <SlideContainer fill={theme.colors.purple1}>
            <Introduction>
              <h2>
                <span>JU:SE </span> Junior to Senior
              </h2>
              <p>주니어에서 씨니어로, 주씨와 함께 성장해보세요!</p>
            </Introduction>
            <ImageContainer>
              <img src='people.png' alt='people' />
            </ImageContainer>
          </SlideContainer>
        </div>
        <div>
          <SlideContainer fill={'#da77a8'}>
            <TeamContainer>
              <h2>Team ChickenMilktea</h2>
              <ChickenMilktea>
                {name.map((el, i) => {
                  return (
                    <span
                      key={i}
                      className={
                        index === i ? 'slide-in' : 'slide-in slide-out'
                      }>
                      <a href={el.github}>
                        <span>
                          {i % 2 === 0 ? 'BE Developer' : 'FE Developer'}
                        </span>
                        {el.name}
                      </a>
                    </span>
                  );
                })}
              </ChickenMilktea>
            </TeamContainer>
            <ImageContainer>
              <img src='치킨밀크티.png' alt='chickenmilktea' />
            </ImageContainer>
          </SlideContainer>
        </div>
      </Slider>
    </CarouselContainer>
  );
};

const CarouselContainer = styled.div`
  min-height: 330px;
  padding-top: 10px;
  .slick-list {
    margin: 0 -30px;
    margin-bottom: 50px;
  }
`;

const SlideContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
  padding: 50px;
  margin: 20px 30px;
  min-height: 310px;
  border-radius: 30px;
  background-color: ${({ fill }) => fill + '35'};
`;

const ImageContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 210px;
  img {
    width: 300px;
  }
`;

const Introduction = styled.div`
  font-size: 22px;
  font-weight: 700;
  line-height: 1.6;
  color: ${({ theme }) => theme.colors.grey5};
  h2 {
    font-size: 28px;
    font-weight: 900;
    color: ${({ theme }) => theme.colors.purple1};
    span {
      font-size: 46px;
    }
  }
`;

const TeamContainer = styled.div`
  width: 412px;
  > h2 {
    font-weight: 700;
    color: ${({ theme }) => theme.colors.purple5};
    font-size: 36px;
  }
`;

const ChickenMilktea = styled.span`
  position: relative;
  height: 60px;
  transition: width 0.2s, height 0.2s;
  display: inline-flex;
  white-space: nowrap;
  text-align: center;
  vertical-align: bottom;
  flex-direction: column;
  > span {
    color: #99a799;
    font-weight: 900;
    font-size: 36px;
    display: block;
    position: absolute;
    top: 100%;
    left: auto;
    right: auto;
    cursor: pointer;
    :first-child {
      opacity: 1;
    }
    > a > span {
      color: ${({ theme }) => theme.colors.black1};
      font-weight: 700;
      font-size: 28px;
      margin-right: 20px;
    }
  }
  > .slide-in {
    animation: slideIn 1s forwards;
    transform: translate3d(0, -100%, 0);
  }
  > .slide-out {
    animation: slideOut 1s forwards;
    transform: translate3d(0, -100%, 0);
  }
  @keyframes slideIn {
    0% {
      opacity: 0;
      transform: translate3d(0, -150%, 0);
    }
    100% {
      opacity: 1;
      transform: translate3d(0, -100%, 0);
    }
  }
  @keyframes slideOut {
    0% {
      opacity: 1;
      transform: translate3d(0, -100%, 0);
    }
    30% {
      opacity: 0;
    }
    100% {
      opacity: 0;
      transform: translate3d(0, -50%, 0);
    }
  }
`;

export default Carousel;
