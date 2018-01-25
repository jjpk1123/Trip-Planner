var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/* ReactJS components to render the sample page */
/* Note the use of className rather than class for ReactJS */

var Header = function (_React$Component) {
  _inherits(Header, _React$Component);

  function Header() {
    _classCallCheck(this, Header);

    return _possibleConstructorReturn(this, (Header.__proto__ || Object.getPrototypeOf(Header)).apply(this, arguments));
  }

  _createClass(Header, [{
    key: "render",
    value: function render() {
      return React.createElement(
        "div",
        { className: "jumbotron" },
        React.createElement(
          "div",
          { className: "container" },
          React.createElement(
            "h1",
            { className: "display-4" },
            "Ezra Huston"
          ),
          React.createElement(
            "p",
            null,
            "I'm a Computer Science major at Colorado State University in Fort Collins, Colorado! Learn more about me, my hobbies, and my skils below!",
            React.createElement("br", null),
            React.createElement(
              "strong",
              null,
              "Contact Information"
            ),
            React.createElement(
              "li",
              null,
              React.createElement(
                "strong",
                null,
                "Phone: "
              ),
              " (303) 775-6602"
            ),
            React.createElement(
              "li",
              null,
              React.createElement(
                "strong",
                null,
                "Mailing Address: "
              ),
              "1113 Hillcrest Dr., Fort Collins, CO 80521"
            ),
            React.createElement(
              "li",
              null,
              React.createElement(
                "strong",
                null,
                "Email: "
              ),
              "ezra.huston@gmail.com"
            )
          )
        )
      );
    }
  }]);

  return Header;
}(React.Component);

var Body = function (_React$Component2) {
  _inherits(Body, _React$Component2);

  function Body() {
    _classCallCheck(this, Body);

    return _possibleConstructorReturn(this, (Body.__proto__ || Object.getPrototypeOf(Body)).apply(this, arguments));
  }

  _createClass(Body, [{
    key: "render",
    value: function render() {
      return React.createElement(
        "div",
        { className: "container" },
        React.createElement(
          "div",
          { className: "row" },
          React.createElement(
            "div",
            { className: "col-md-4" },
            React.createElement(
              "h2",
              null,
              "Skills"
            ),
            React.createElement(
              "p",
              null,
              "I am proficient in ",
              React.createElement(
                "li",
                null,
                "Java"
              ),
              React.createElement(
                "li",
                null,
                "C++"
              ),
              React.createElement(
                "li",
                null,
                "C"
              ),
              React.createElement(
                "li",
                null,
                "Python"
              ),
              React.createElement(
                "li",
                null,
                "HTML"
              ),
              "I am working on learning Bootstrapv4, CodePen, and JavaScript currently. I also work well in teams, and have extensive previous experience in such. "
            )
          ),
          React.createElement(
            "div",
            { className: "col-md-4" },
            React.createElement(
              "h2",
              null,
              "Education"
            ),
            React.createElement(
              "p",
              null,
              React.createElement(
                "li",
                null,
                React.createElement(
                  "strong",
                  null,
                  "B.S. in Computer Science"
                )
              ),
              "Colorado State University, Fort Collins, CO",
              React.createElement("br", null),
              "August 2015 - current",
              React.createElement(
                "li",
                null,
                React.createElement(
                  "strong",
                  null,
                  "High School Diploma"
                )
              ),
              "Earned May 2015 from Cherokee Trail High School"
            )
          ),
          React.createElement(
            "div",
            { "class": "col-md-4" },
            React.createElement(
              "h2",
              null,
              "Work Experience"
            ),
            React.createElement(
              "p",
              null,
              React.createElement(
                "li",
                null,
                React.createElement(
                  "strong",
                  null,
                  "Discount Tire (Feb. 2014 - Aug. 2015)"
                )
              ),
              "At Discount Tire I worked as a Service Coordinator, usually running a team of four technicians while they performed their work on vehicles.",
              React.createElement(
                "li",
                null,
                React.createElement(
                  "strong",
                  null,
                  "Discovery Research"
                )
              ),
              "I worked as an interviewer for a research firm. I learned a lot of customer service skills during my time at Discovery Research, and they have helped me greatly throught my education."
            )
          )
        )
      );
    }
  }]);

  return Body;
}(React.Component);

var Main = function (_React$Component3) {
  _inherits(Main, _React$Component3);

  function Main() {
    _classCallCheck(this, Main);

    return _possibleConstructorReturn(this, (Main.__proto__ || Object.getPrototypeOf(Main)).apply(this, arguments));
  }

  _createClass(Main, [{
    key: "render",
    value: function render() {
      return React.createElement(
        "div",
        null,
        React.createElement(Header, null),
        React.createElement("hr", null),
        React.createElement(Body, null)
      );
    }
  }]);

  return Main;
}(React.Component);

ReactDOM.render(React.createElement(Main, null), document.getElementById("root"));